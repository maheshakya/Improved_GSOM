/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core;

import com.gsom.objects.GNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thilina
 */
public class FBR extends DeleteBase {

    private ArrayList<String> T = new ArrayList<String>();
    private Map<String, Integer> freq = new HashMap<String, Integer>();

    @Override
    public void update(String Coordinate) {
        if (T.contains(Coordinate)) {
            int index = T.indexOf(Coordinate);
            if (index > T.size() / 3) {
                freq.put(Coordinate, freq.get(Coordinate) + 1);
            }

            T.remove(index);
            T.add(0, Coordinate);
        } else {
            T.add(Coordinate);
            freq.put(Coordinate, 1);
        }
    }

    @Override
    public void adjust(Map<String, GNode> nodeMap) {
        if (nodeMap.size() > 4) {
            int min = Integer.MAX_VALUE;
            int i_ = 0;

            if (T.size() > 4) {
                for (int i = T.size() - 1; i > 2 * T.size() / 3; i--) {
                    if (freq.get(T.get(i)) < min) {
                        min = freq.get(T.get(i));
                        i_ = i;
                    }
                }

                nodeMap.remove(T.get(i_));
                freq.remove(T.get(i_));
                T.remove(i_);
            }
        }
    }

    public int get_Size() {
        return T.size();
    }
}
