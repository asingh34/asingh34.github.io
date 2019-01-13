import java.awt.*;
public class Square  {

	/* web colors table */
    private static final int WHITE = 0xFFFFFF;
    private static final int SILVER = 0xC0C0C0;
    private static final int GRAY = 0x808080;
    private static final int BLACK = 0x000000;
    private static final int RED = 0xFF0000;
    private static final int MAROON = 0x800000;
    private static final int YELLOW = 0xFFFF00;
    private static final int OLIVE = 0x808000;
    private static final int LIME = 0x00FF00;
    private static final int GREEN = 0x008000;
    private static final int AQUA = 0x00FFFF;
    private static final int TEAL = 0x008080;
    private static final int BLUE = 0x0000FF;
    private static final int NAVY = 0x000080;
    private static final int FUCHSIA = 0xFF00FF;
    private static final int PURPLE = 0x800080;

    int value;

    public Square() {
      this(0);
    }

    public Square(int num) {
      value = num;
    }


    public boolean isEmpty() {
      return value == 0;
    }

    public Color textColor() {
      return new Color(WHITE);
    }

    public Color bgColor() {
	/* the basic web colors table. Might need to shuffle them to give brighters to highers */
	
      switch (value) {
        case 2:    return new Color(MAROON);
        case 4:    return new Color(RED);
        case 8:    return new Color(OLIVE);
        case 16:   return new Color(GREEN);
        case 32:   return new Color(LIME);
        case 64:   return new Color(YELLOW);
        case 128:  return new Color(TEAL);
        case 256:  return new Color(AQUA);
        case 512:  return new Color(BLUE);
        case 1024: return new Color(NAVY);
        case 2048: return new Color(FUCHSIA);
        case 4096: return new Color(PURPLE);
      }
      return new Color(SILVER);
    }

  }



