/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codetest.infrastructure.resources;

import codetest.connection.HttpConnection;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import testdata.TestData;

/**
 *
 * @author Yvonne Elsie
 */
public class InfrastructureResources {
    public String infrastructure_url = "https://raw.githubusercontent.com/onaio/ona-tech/master/data/water_points.json";
    
    //Uncomment to use this as the TestData source
    //public TestData testData = new TestData();
    //public String infrastructure_url = testData.TestData();
    
    HttpConnection newHttpConnection = new HttpConnection(infrastructure_url);
    
    private String jsonResponse = "";
    private String communities_villages;
    private String water_functioning;
    
    /*
        {
            number_functional: …,
            number_water_points: {
              communityA: …,
            },
            community_ranking: …
        }
    */
    
    public InfrastructureResources(){
        getJSON(infrastructure_url, 60);
    }
    
    public String getJSON(String infrastructure_url, int timeout){
        jsonResponse = newHttpConnection.excutePost(infrastructure_url,timeout);
        //System.out.println("JSON Response: "+jsonResponse);
        getAllInfrustructureResources(jsonResponse);
        getFunctionalWaterPoint(jsonResponse);
        getBrokenWaterPointsPerCommunity(jsonResponse);
        getWaterPointPerCommunity(jsonResponse);
        /*try {  
            JSONArray jsonArray = new JSONArray(jsonResponse);
            System.out.println("\n InfrastructureResource: " + jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/   
        
        return jsonResponse;
    }
    
    public void getAllInfrustructureResources(String jsonString){
        System.out.println();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++){   // iterate through jsonArray 
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
                    System.out.println("InfrastructureResource Object (s)" + i + ": " + jsonObject);
            }
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }
    
    public void getFunctionalWaterPoint(String jsonString){
        System.out.println("Resources with Functional Water Points ");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            int functional_water_points = 0;
            for(int i=0 ; i< count; i++){   // iterate through jsonArray 
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
                if(jsonObject.has("water_functioning") && jsonObject.getString("water_functioning").equals("yes")){
                    functional_water_points += 1;
                    System.out.println("" + i + ": " + jsonObject);
                }
            }
            System.out.println("Functional Water Points: "+functional_water_points);
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }
    
    public void getWaterPointPerCommunity(String jsonString){
        System.out.println("Water Points Per Community:");        
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int count = jsonArray.length(); // get totalCount of all jsonObjects
            String[] communities_villages_array = new String[count]; 
            ArrayList villages_list = new ArrayList();
            ArrayList villages_unrepeated_list = new ArrayList();
            
            System.out.println("Number of Water Points: "+count);
            int broken_water_points=0;
            for(int i=0 ; i< count; i++){   // iterate through jsonArray 
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
                if(jsonObject.has("communities_villages")){
                    broken_water_points+=1;
                    villages_list.add(""+jsonObject.get("communities_villages"));
                    //System.out.println("" + i + ": " + jsonObject.get("communities_villages"));
                }
            }
            Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            set.addAll(villages_list);
            villages_unrepeated_list = new ArrayList<>(set);
            
            Iterator<String> VillageIterators = villages_unrepeated_list.iterator();
            int number_of_water_points;
            int index_value = 0;
                while (VillageIterators.hasNext()) {
                    String current_comparison_village = VillageIterators.next();
                    System.out.print(current_comparison_village+"\n");//Specific Community Villages
                    Iterator<String> VillageIterator = villages_list.iterator();
                    number_of_water_points = 0;
                        while (VillageIterator.hasNext()) {
                           index_value+=1;
                           if(VillageIterator.next().equals(current_comparison_village)){
                               try{
                                   JSONObject jsonObject = jsonArray.getJSONObject(index_value);
                                   System.out.println("" + index_value + ": " + jsonObject);
                                   //System.out.println("\t"+VillageIterator.next()+" "+current_comparison_village);
                                   number_of_water_points +=1;                                    
                               }catch(Exception ex){
                                   ex.printStackTrace();
                               }                               
                           }                           
                        }
                      System.out.print(" #"+number_of_water_points+" \n");
                    //System.out.print(" "+number_of_water_points);
                    //break;
                }

            //System.out.println("Village Names: "+villages_unrepeated_list);
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }
    
    public void getBrokenWaterPointsPerCommunity(String jsonString){
        System.out.println("Resources with Broken Water Points ");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            System.out.println("Number of Water Points: "+count);
            int broken_water_points=0;
            for(int i=0 ; i< count; i++){   // iterate through jsonArray 
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
                if(jsonObject.has("water_not_functioning")){
                    broken_water_points+=1;
                    System.out.println("" + i + ": " + jsonObject);
                }
            }
            System.out.println("Total Broken Waterpoints: "+broken_water_points);
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }    
}