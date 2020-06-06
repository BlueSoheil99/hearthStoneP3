package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;


public class ExitButton extends IconButton {
    private static ExitButton instance;

    private ExitButton() {
        super(GuiConstants.getInstance().getExitIconPath());
    }

    public static ExitButton getInstance() {
        if (instance == null) instance = new ExitButton();
        return instance;
    }

    @Override
    protected void addListener() {
        addActionListener(e -> {
            Logger.log(LogTypes.CLICK_BUTTON, "button: EXIT selected .");
            Administer.getInstance().runExit();
        });
    }
}
