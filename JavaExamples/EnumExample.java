public class EnumExample
{
    enum Direction
    {
        EAST(180), WEST(23), NORTH(0), SOUTH(270);

        private final int angle;

        private Direction(final int angle)
        {
            this.angle = angle;
        }

        public int getAngle()
        {
            return angle;
        }
    }

    public static void main(String[] args)
    {
        Direction north = Direction.NORTH;
        System.out.println(north.angle);

    }


}