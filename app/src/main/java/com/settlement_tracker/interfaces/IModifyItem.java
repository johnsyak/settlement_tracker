package com.settlement_tracker.interfaces;

import android.view.View;

/**
 * Created by John on 9/26/2017.
 */

public interface IModifyItem {
    void itemSelect(View view, int position);
    void itemDelete(View view, int position);
}
