/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codetest;

import codetest.infrastructure.resources.InfrastructureResources;

/**
 *
 * @author Steven Mugo
 */
public class CodeTest {

    public static InfrastructureResources newInfrastructureResources;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Fetching Infrustructure Resources");
        newInfrastructureResources = new InfrastructureResources();
        
    }
    
}
