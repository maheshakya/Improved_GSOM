/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core;

import com.gsom.objects.GNode;
import java.util.Map;

/**
 *
 * @author thilina
 */
public class Fritzke extends DeleteBase {

    private int nodes = 4;
    private double p_sure = 0.98;

    public Fritzke(double sure) {
        p_sure = sure;
    }

    @Override
    public void update(String Coordinate) {
    }

    @Override
    public void adjust(Map<String, GNode> nodeMap) {

        int new_nodes = nodeMap.size() - nodes;

        nodes = nodeMap.size();

        int k_remove = 0;

        if (new_nodes > 0 && iter > 0) {
            int n_dist = iter / new_nodes;

            k_remove = (int) Math.ceil(nodes * n_dist * (1 - Math.pow(1 - Math.pow(p_sure, 1.0 / nodes), 1.0 / (n_dist + 1))));
        }

        if (k_remove > 0) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                if ((iter - entry.getValue().getK_iterate()) > k_remove) {
                    nodeMap.remove(entry.getKey());
                }
            }
        }
    }

}
