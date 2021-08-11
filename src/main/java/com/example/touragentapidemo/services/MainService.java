package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dtos.OfferDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MainService {

    private final RabbitTemplate rabbitTemplate;
    private final ImageService imageService;

    public MainService(RabbitTemplate rabbitTemplate, ImageService imageService) {
        this.rabbitTemplate = rabbitTemplate;
        this.imageService = imageService;
    }

    public void putOffersToQueue(OfferDTO offerDTO, long usersRequestsId){
        File image = convertOfferDtoToImage(offerDTO);
        String url = imageService.uploadImageToFireBase(image);
        Map<String, String> offerMap = new HashMap<>();
        offerMap.put("RequestId", offerDTO.getRequest());
        offerMap.put("OfferImageUrl", url);
        offerMap.put("UsersRequestsId", String.valueOf(usersRequestsId));
        rabbitTemplate.convertAndSend("my_offer_queue", offerMap);

    }

    public File convertOfferDtoToImage(OfferDTO offerDTO){
        int width = 1000;
        int height = 1500;

        BufferedImage img = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 50);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();

        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.setColor(Color.BLACK);

        int nextLinePosition=200;         //// bu yuxaridan olan mesafedi 100 idi
        int fontSize = 300;                   /// evvel 45 idi sonra 100

        drawString(g2d, "   qiymet : " + offerDTO.getPrice(), 0, nextLinePosition, width);
        nextLinePosition = nextLinePosition + fontSize;
        drawString(g2d, "   tarix araligi : " + offerDTO.getDateRange(), 0, nextLinePosition, width);
        nextLinePosition = nextLinePosition + fontSize;
        drawString(g2d, "   elave melumatlar : " + offerDTO.getAdditionalInfo(), 0, nextLinePosition, width);
        nextLinePosition = nextLinePosition + fontSize;
        drawString(g2d, "   Reply eden zaman kontakt ucun informasiyanizi daxil edin " , 0, nextLinePosition, width);

        g2d.dispose();
        File image = new File("OfferImage.png");
        try {
            ImageIO.write(img, "png", image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
    }

    public void drawString(Graphics g, String s, int x, int y, int width)
    {
        // FontMetrics gives us information about the width,
        // height, etc. of the current Graphics object's Font.
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y;

        String[] words = s.split(" ");

        for (String word : words)
        {
            // Find out thw width of the word.
            int wordWidth = fm.stringWidth(word + " ");

            // If text exceeds the width, then move to next line.
            if (curX + wordWidth >= x + width)
            {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            // Move over to the right for next word.
            curX += wordWidth;
        }
    }
}
