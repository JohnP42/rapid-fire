package com.johnp.rf;

import com.johnp.framework.Screen;
import com.johnp.framework.implementation.AndroidGame;

public class RapidFire extends AndroidGame {

    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this); 
    }

    @Override
    public void onBackPressed() {
    getCurrentScreen().backButton();
    }
}
