package com.settlement_tracker.interfaces;

import com.settlement_tracker.sqlite.model.Attributes;

/**
 * Created by John on 9/26/2017.
 */

public interface FragmentCommunicator {
    void writeToDb(String text, String tag);
    void checksToDb(boolean checked, int tag);
    void onGenderClick(int gender);
    void onAdjustHeadClick(int value, int injury, int baseStat);
    void onAdjustLegsClick(int value, int injury, int baseStat);
    void onAdjustArmClick(int value, int injury, int baseStat);
    void onAdjustWaistClick(int value, int injury, int baseStat);
    void onAdjustBodyClick(int value, int injury, int baseStat);
    void onProfClick(int value);
    void onBrainClick(int value);
    void onSurvivalClick(int value);
    void onHuntClick(int value);
    void onCourageClick(int value);
    void onUnderstandingClick(int value);
    void onStatConfirmClick(Attributes a);
}
