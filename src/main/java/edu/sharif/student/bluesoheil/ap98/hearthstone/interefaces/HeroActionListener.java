package edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.WeaponActualCard;

import java.util.EventListener;

public interface HeroActionListener extends EventListener {

    void playHeroPower();
    void playWeapon(WeaponActualCard playedWeapon);
    void selectHero();

}
