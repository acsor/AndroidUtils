package org.n0ne.utils.os;

import android.os.Parcelable;

public abstract class ViewAttributes implements Parcelable {

    protected Parcelable superState;

    public void setSuperState (Parcelable superState) {
        this.superState = superState;
    }

    public Parcelable getSuperState () {
        return superState;
    }

}
