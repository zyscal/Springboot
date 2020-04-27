package com.example.demo.controller;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PowernetmapTest {

    public static void main(String[] args){
        Set<String> finda = new HashSet<>();
        boolean a = finda.add("aaa");
        System.out.println(a);
        a = finda.add("aaa");
        System.out.println(a);
        a= finda.remove("aaa");
        System.out.println(a);
        a = finda.remove("aaa");
        System.out.println(a);

    }
}