/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lc.world.objectes;

import lc.item.block.Block;

/**
 *
 * @author all
 */
public interface CollisionGroundExpose{
    
        public boolean expose(Block c, int xId, int yId);
}
