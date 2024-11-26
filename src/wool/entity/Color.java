package wool.entity;




public class Color {
    @FunctionalInterface
    interface Calc {
        float callback(float a, float b);
    }
    //mixin fac
    public static float fac(float a, float b, float res, float fac) {
        if (fac <= .5) {
            //A=-2x+1,C=2kx
            return (-2 * fac + 1) * a + (2 * fac) * res;
        } else {
            //B=2x-1,C=-2x+2
            return (2 * fac - 1) * b + (-2 * fac + 2) * res;
        }
    }

    public static float calc(float a, float b, float fac, Calc c) {
        return Color.fac(a, b, Math.max(Math.min(c.callback(a, b), 1), 0), fac);
    }

    public static float mix(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return (a + b) / 2;
        });
    }

    public static float min(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return Math.min(a, b);
        });
    }

    public static float max(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return Math.max(a, b);
        });
    }

    public static float mul(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a * b;
        });
    }

    public static float screen(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return 1 - (1 - a) * (1 - b);
        });
    }

    public static float burn(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a - (1 - a) * (1 - b) / b;
        });
    }

    public static float dodge(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + (a * b) / (1 - b);
        });
    }

    public static float linearBurn(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + b - 1;
        });
    }

    public static float linearDodge(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + b;
        });
    }

    public static float overlap(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            if (a <= .5) {
                return a * b / 2;
            } else {
                return 1 - (1 - a) * (1 - b) / 2;
            }
        });
    }

    public static float hardLight(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            if (b <= .5) {
                return a * b / 2;
            } else {
                return 1 - (1 - a) * (1 - b) / 2;
            }
        });
    }

    public static float softLight(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            if (b <= .5) {
                return a * b / 2 + a * a * (1 - 2 * b);
            } else {
                return (float) (a * (1 - b) / 2 + Math.sqrt(a) * (2 * b - 1));
            }
        });
    }

    public static float vividLight(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            if (b <= .5) {
                return a - (1 - a) * (1 - 2 * b) / (2 * b);
            } else {
                return a + a * (2 * b - 1) / (2 * (1 - b));
            }
        });
    }

    public static float pinLight(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            if (b <= .5) {
                return Math.min(a, 2 * b);
            } else {
                return Math.min(a, 2 * b - 1);
            }
        });
    }

    public static float linearLight(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + 2 * b - 1;
        });
    }

    public static float hardMix(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + b > 1 ? 1 : 0;
        });
    }

    public static float exclusion(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return a + b - a * b / 2;
        });
    }

    public static float difference(float x, float y, float fac) {
        return Color.calc(x, y, fac, (float a, float b) -> {
            return Math.abs(a - b);
        });
    }

    //
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;

    //
    public Color alpha(float a) {
        this.a = this.clampNumber(a);
        return this;
    }

    public Color alphaMul(float a) {
        this.a = this.clampNumber(a * this.a);
        return this;
    }

    public Color alphaAdd(float a) {
        this.a = this.clampNumber(a + this.a);
        return this;
    }

    /*
    public String hex() {
        return "#" +
                Math.round(this.r * 255).to(16).padStart(2, '0') +
                Math.round(this.g * 255).toString(16).padStart(2, '0') +
                Math.round(this.b * 255).toString(16).padStart(2, '0') +
                Math.round(this.a * 255).toString(16).padStart(2, '0');
    }*/
    @FunctionalInterface
    interface CalcColor {
        float callback(float a, float b, float fac);
    }

    public Color calc(Color a, float fac, CalcColor c) {
        var copy = new Color(this);
        copy.r = c.callback(copy.r, a.r, fac);
        copy.g = c.callback(copy.g, a.g, fac);
        copy.b = c.callback(copy.b, a.b, fac);
        copy.a = (a.a + copy.a) / 2;
        return copy.clamp();
    }

    public Color min(Color color) {
        return this.min(color, .5f);
    }

    public Color min(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.min(a, b, c);
        });
    }

    public Color max(Color color) {
        return this.max(color, .5f);
    }

    public Color max(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.max(a, b, c);
        });
    }

    public Color mix(Color color) {
        return this.mix(color, .5f);
    }

    public Color mix(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.mix(a, b, c);
        });
    }

    public Color screen(Color color) {
        return this.screen(color, .5f);
    }

    public Color screen(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.screen(a, b, c);
        });
    }

    public Color burn(Color color) {
        return this.burn(color, .5f);
    }

    public Color burn(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.burn(a, b, c);
        });
    }

    public Color dodge(Color color) {
        return this.dodge(color, .5f);
    }

    public Color dodge(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.dodge(a, b, c);
        });
    }

    public Color linearBurn(Color color) {
        return this.linearBurn(color, .5f);
    }

    public Color linearBurn(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.linearBurn(a, b, c);
        });
    }

    public Color linearDodge(Color color) {
        return this.linearDodge(color, .5f);
    }

    public Color linearDodge(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.linearDodge(a, b, c);
        });
    }

    public Color overlay(Color color) {
        return this.overlay(color, .5f);
    }

    public Color overlay(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.overlap(a, b, c);
        });
    }

    public Color hardLight(Color color) {
        return this.hardLight(color, .5f);
    }

    public Color hardLight(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.hardLight(a, b, c);
        });
    }

    public Color softLight(Color color) {
        return this.softLight(color, .5f);
    }

    public Color softLight(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.softLight(a, b, c);
        });
    }

    public Color vividLight(Color color) {
        return this.vividLight(color, .5f);
    }

    public Color vividLight(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.vividLight(a, b, c);
        });
    }

    public Color pinLight(Color color) {
        return this.pinLight(color, .5f);
    }

    public Color pinLight(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.pinLight(a, b, c);
        });
    }

    public Color linearLight(Color color) {
        return this.linearLight(color, .5f);
    }

    public Color linearLight(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.linearLight(a, b, c);
        });
    }

    public Color hardMix(Color color) {
        return this.hardMix(color, .5f);
    }

    public Color hardMix(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.hardMix(a, b, c);
        });
    }

    public Color difference(Color color) {
        return this.difference(color, .5f);
    }

    public Color difference(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.difference(a, b, c);
        });
    }

    public Color exclusion(Color color) {
        return this.exclusion(color, .5f);
    }

    public Color exclusion(Color color, float fac) {
        return this.calc(color, fac, (float a, float b, float c) -> {
            return Color.exclusion(a, b, c);
        });
    }

    public Color clamp() {
        this.r = this.clampNumber(this.r);
        this.g = this.clampNumber(this.g);
        this.b = this.clampNumber(this.b);
        this.a = this.clampNumber(this.a);
        return this;
    }

    public float clampNumber(float val) {
        return Math.min(1f, Math.max(val, 0f));
    }

    public Color fromHex(String hex) {
        hex = hex.replace("#", "");
        if (hex.length() == 3) {
            hex = hex.charAt(0) + "" + hex.charAt(0) + hex.charAt(1) + "" + hex.charAt(1) + hex.charAt(2) + "" + hex.charAt(2);
        } else if (hex.length() == 4) {
            hex = hex.charAt(0) + "" + hex.charAt(0) + hex.charAt(1) + "" + hex.charAt(1) + hex.charAt(2) + "" + hex.charAt(2) + hex.charAt(3) + "" + hex.charAt(3);
        } else if (hex.length() != 6 && hex.length() != 8) {
            throw new IllegalArgumentException("Invalid hex color format");
        }
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        float a = 1;
        if (hex.length() == 8) {
            a = (float) (Integer.parseInt(hex.substring(6, 8), 16) / 255.0);
        }
        float red = r / 255.0f;
        float green = g / 255.0f;
        float blue = b / 255.0f;
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = a;
        return this.clamp();
    }

    public Color(String hex) {
        try {
            this.fromHex(hex);
        } catch (Exception e) {
            this.fromHex("#ffffff");
        }
    }

    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public Color(arc.graphics.Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public arc.graphics.Color to() {
        return new arc.graphics.Color(this.r, this.g, this.b, this.a);
    }

}