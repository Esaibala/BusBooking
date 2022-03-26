package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;


public class Booking {
    String passengerName;
    int busNo;
    Date date;
    String dateS;
    Booking()  {
        try {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your Name :");
        this.passengerName = scan.nextLine();
        System.out.print("Enter the busNumber :");
        this.busNo =scan.nextInt();

        System.out.print("Enter the date (yyyy-MM-dd):");
        scan.nextLine();
        String dateInput=scan.nextLine();
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
       dateS =dateInput;
            date = dateFormat.parse(dateInput);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public boolean isAvailable(ArrayList<Booking> bookings,ArrayList<Bus> bus)
    {
        int capacity = 0;
        for(Bus cap:bus)
        {
            if(cap.getBusNo()==this.busNo )
                capacity=cap.getCapacity();
        }
        int bookingcount =0;
        for(Booking book: bookings)
        {
            if(book.date.equals(date)&&book.busNo==busNo)
                bookingcount++  ;
        }
       return bookingcount <capacity;
    }
    public void  insert(Connection con) throws SQLException
    {

        try {
            PreparedStatement stmt = con.prepareStatement("insert into Booking(passenger_name ,bus_no ,date) values(?,?,?)" ) ;
            stmt.setString(1,passengerName);
            stmt.setInt(2, busNo);

            stmt.setDate(3, java.sql.Date.valueOf(dateS));
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
