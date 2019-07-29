package app.com.temanhijrah.base;

import java.util.ArrayList;


public interface BaseView<M> {
    void onLoad(ArrayList<M> data);
}
