/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.ServiceNotFoundException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/orders")
public class OrdersAPIController {

    @Autowired
    RestaurantOrderServices ros;

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<?> handleGetRecurso(){
        try {
            Set<Integer> tableOrders = ros.getTablesWithOrders();
            List<Map<String, Object>> ordersList = new ArrayList<>();

            for (Integer i : tableOrders) {
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("Table", i);
                orderMap.put("Total", ros.calculateTableBill(i));

                String orderString = ros.getTableOrder(i).toString().replace("In", ",");
                String[] orderItems = orderString.split(",");
                List<String> orderItemList = new ArrayList<>();
                for (String orderItem : orderItems) {
                    orderItemList.add(orderItem.trim());
                }
                orderMap.put("Order", orderItemList);
                ordersList.add(orderMap);
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(ordersList);

            return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
        } catch (OrderServicesException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
    

