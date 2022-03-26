package com.company;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;

class Bus
{

    private int busNo;
    private boolean AC;
    private int capacity;
    Bus(int busNo,boolean Ac,int capacity)
    {
      this.AC=Ac;
      this.busNo= busNo;
      this.capacity=capacity;

    }

    public int getBusNo() {
        return busNo;
    }

    public void setBusNo(int busNo) {
        this.busNo = busNo;
    }

    public boolean isAC() {
        return AC;
    }

    public void setAC(boolean AC) {
        this.AC = AC;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String toString(){
        return "BusNumber "+this.busNo+"\t"+"Ac "+this.AC+"\t"+"capacity "+this.capacity+"\t";
    }
    public void  insert(Connection con) throws  SQLException
    {

        try {
            PreparedStatement stmt = con.prepareStatement("insert into Bus(bus_no,AC,capacity) values(?,?,?)" ) ;
            stmt.setInt(1,busNo);
            stmt.setString(2, String.valueOf(AC));
            stmt.setInt(3,capacity);
            stmt.executeUpdate();
            stmt.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}

public class Main {


    public static void main(String[] args)  {
        // write your code here
        Scanner scan = new Scanner(System.in);
        ArrayList<Bus> bus = new ArrayList<Bus>();
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bus_Booking", "root", "@Esaibala83");
            bus.add(new Bus(1, true, 20));
            bus.add(new Bus(2, false, 45));
            bus.add(new Bus(3, true, 30));
            for(Bus details:bus) {
                System.out.println(details);
//                details.insert(con);
            }





        int userinput = 1;
        while (userinput == 1) {
            System.out.print("You have booking Press 1 or 2 exit :");
            userinput = scan.nextInt();
            if (userinput == 1) {

                Booking booking = new Booking();
                if (booking.busNo > 0 && booking.busNo <= bus.size()) {
                    if (booking.isAvailable(bookings, bus)) {
                        System.out.println("Your booking was confirmed");
                        bookings.add(booking);
                        booking.insert(con);

                    } else
                        System.out.println("Sorry bus was full");
                }
            }

            }

        } catch (SQLException e)
        {
          e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
