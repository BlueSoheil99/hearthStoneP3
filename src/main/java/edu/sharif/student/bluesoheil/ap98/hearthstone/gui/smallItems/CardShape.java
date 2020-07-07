package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

//todo change JButton to JLabel and set mouseListener for it.
// for different occasions you can have different mouseEntered methods which can be very helpful for a better gui . phase4 inshalla !
public class CardShape extends JButton {
    private static final int CARD_WIDTH = GuiConstants.getInstance().getCardWidth();
    private static final int CARD_HEIGHT = GuiConstants.getInstance().getCardHeight();
    private static final int PASSIVE_WIDTH = GuiConstants.getInstance().getPassiveWidth();
    private static final int PASSIVE_HEIGHT = GuiConstants.getInstance().getPassiveHeight();
    private static ImageIcon backCover;
    static {
        updateBackCover();
    }
    private String cardName;
    protected ImageIcon icon;

    public CardShape(String cardName, BufferedImage image) {
        this(cardName, image, true);
    }

    public CardShape(String cardName, String description) { //this constructor is used for creating passives
        this(cardName,  null, true);
        setPreferredSize(new Dimension(PASSIVE_WIDTH, PASSIVE_HEIGHT));
        setBackground(new Color(192, 135, 107));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(62, 164, 176)));
        JLabel name = new JLabel(cardName);
        JLabel des = new JLabel("<html>" + description.replaceAll("\n", "<br/>") + "</html>");
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.weighty = 1;
        add(name, gc);
        gc.gridy++;
        gc.weighty = 2;
        add(des, gc);
    }

    public CardShape(String cardName, BufferedImage image, boolean owned) {
        this.cardName = cardName;
        if (image != null) icon = new ImageIcon(image);
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        //i manually changed my images dimensions so we don't need methods below
//        Image img = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
//        icon = new ImageIcon( icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH));
        if (icon != null) setIcon(icon);
        setOpaque(false);
        setContentAreaFilled(false);
        if (!owned) {
            // 1 red border
//          Border border = getBorder();
//          Border border1 = BorderFactory.createMatteBorder(5,10,10,5,Color.RED);
//          setBorder(BorderFactory.createCompoundBorder(border1 , border));
            // 2  red image
//            BufferedImage img =copyImage(image);
//            icon = new ImageIcon(colorImage(img));
            // 3  different background
            setContentAreaFilled(true);
            setBackground(new Color(192, 94, 78));
            setOpaque(true);
        }
    }

    public static void updateBackCover() {
        backCover =  new ImageIcon(ImageLoader.loadImage(GuiConstants.getInstance().getDefaultBackCoverPath()));
    }

    public static CardShape copyCardShape(CardShape shape) {
        return new CardShape(shape.cardName, (BufferedImage) shape.icon.getImage());
    }

    public String getCardName() {
        return cardName;
    }

    public void showBackOfCard(boolean showBack) {
        if (showBack) {
            setIcon(backCover);
        } else {
            setIcon(icon);
        }
    }

    private BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p = image.getRGB(xx, yy);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(xx, yy, p);
            }
        }
        return image;
    }

    private BufferedImage copyImage(BufferedImage coverImage) {
        ColorModel colorModel = coverImage.getColorModel();
        boolean isAlphaPremultiplied = coverImage.isAlphaPremultiplied();
        WritableRaster raster = coverImage.copyData(null);
        return new BufferedImage(colorModel, raster,
                isAlphaPremultiplied, null);
    }

}
