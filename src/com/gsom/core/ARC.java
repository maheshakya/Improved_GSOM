/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core;

import com.gsom.objects.GNode;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author thilina
 */
public class ARC extends DeleteBase {

    private ArrayList<String> L1 = new ArrayList<String>();
    private ArrayList<String> L2 = new ArrayList<String>();

    @Override
    void update(String Coordinate) {
        if (L1.contains(Coordinate)) {
            L1.remove(Coordinate);
            if (!L2.contains(Coordinate)) {
                L2.add(Coordinate);
            } else {
                L2.remove(Coordinate);
                L2.add(0, Coordinate);
            }
        } else {
            L1.add(Coordinate);
        }
    }

    @Override
    void adjust(Map<String, GNode> nodeMap) {
        if (nodeMap.size() > 4) {
            if (L1.size() > L2.size()) {
                nodeMap.remove(L1.get(L1.size() - 1));
                L1.remove(L1.size() - 1);
            } else {
                nodeMap.remove(L1.get(L1.size() - 1));
                L2.remove(L2.size() - 1);
            }
        }
    }

    public int get_Size() {
        return L1.size();
    }
}
