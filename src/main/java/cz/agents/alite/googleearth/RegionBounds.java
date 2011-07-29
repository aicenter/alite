package cz.agents.alite.googleearth;

public class RegionBounds
{
    public double north;
    public double south;
    public double east;
    public double west;
    
    public RegionBounds()
    {
        north = 0;
        south = 0;
        east = 0;
        west = 0;        
    }

    public RegionBounds(double north, double south, double east, double west)
    {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }
    
}
