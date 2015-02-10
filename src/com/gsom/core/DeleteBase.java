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
public abstract class DeleteBase {

    abstract void update(String Coordinate);

    abstract void adjust(Map<String, GNode> nodeMap);
}
