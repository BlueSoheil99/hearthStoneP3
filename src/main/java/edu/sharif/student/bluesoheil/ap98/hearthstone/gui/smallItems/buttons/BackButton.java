package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;


public class BackButton extends IconButton {
    private static BackButton instance;

    private BackButton() {
        super(GuiConstants.getInstance().getBackIconPath());
    }

    public static BackButton getInstance() {
        if (instance == null) instance = new BackButton();
        return instance;
    }

    @Override
    protected void addListener() {
        addActionListener(e -> {
            Logger.log(LogTypes.CLICK_BUTTON, "button: BACK selected .");
            Administer.getInstance().back();
        });
    }
}
