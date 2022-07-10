package com.tajacks.algorithms.algxj.dlx.nodes;

import com.tajacks.algorithms.algxj.dlx.exception.NodeException;

public interface DownLinkable<T extends Node> {
    T addBelow(T n) throws NodeException;
}
