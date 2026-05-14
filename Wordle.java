import java.util.*;

/**
 * Wordle — Terminal Edition (single-file version)
 *
 * Compile:  javac Wordle.java
 * Run:      java Wordle
 *
 * Works in any modern terminal (Linux, macOS, Windows Terminal, VS Code).
 */
public class Wordle {

    // ===== ANSI color codes for the tiles =====
    static final String RESET     = "\u001B[0m";
    static final String BOLD      = "\u001B[1m";
    static final String BG_GREEN  = "\u001B[42m\u001B[30m";   // green bg, black fg
    static final String BG_YELLOW = "\u001B[43m\u001B[30m";
    static final String BG_GRAY   = "\u001B[100m\u001B[97m";

    // ===== Word list — feel free to add more =====
    static final String[] WORDS = {
        "ABOUT","ABOVE","AGENT","APPLE","BEACH","BEGIN","BLACK","BLAME","BLEND","BLOCK",
        "BLOOD","BOARD","BRAIN","BRAND","BREAD","BREAK","BRICK","BRING","BROWN","BUILD",
        "CHAIR","CHART","CHILD","CLEAN","CLICK","CLIMB","CLOCK","CLOUD","COULD","COUNT",
        "COVER","CRANE","CREAM","CRIME","CROSS","CROWD","DANCE","DEPTH","DOUBT","DREAM",
        "DRESS","DRINK","DRIVE","EARLY","EARTH","EIGHT","EMPTY","ENJOY","ENTER","EQUAL",
        "EVENT","EXTRA","FAITH","FALSE","FANCY","FAULT","FIELD","FIGHT","FINAL","FIRST",
        "FLAME","FLASH","FLOOD","FLOOR","FOCUS","FORCE","FOUND","FRESH","FROST","FRUIT",
        "GHOST","GIANT","GLASS","GLORY","GRACE","GRADE","GRAIN","GRAND","GRANT","GRAPE",
        "GRASS","GREAT","GREEN","GROUP","GUARD","GUESS","GUEST","HAPPY","HEART","HEAVY",
        "HORSE","HOTEL","HOUSE","HUMAN","IMAGE","INDEX","INPUT","JUDGE","JUICE","KNIFE",
        "LAUGH","LEARN","LEAVE","LEMON","LIGHT","LIMIT","LOCAL","LOGIC","LUCKY","MAGIC",
        "MAJOR","METAL","MIGHT","MONEY","MONTH","MOUTH","MOVIE","MUSIC","NEVER","NIGHT",
        "NOBLE","NOISE","NORTH","NOVEL","NURSE","OCEAN","OFFER","ORDER","OTHER","PAINT",
        "PANEL","PAPER","PARTY","PEACE","PHONE","PHOTO","PIANO","PIECE","PILOT","PLACE",
        "PLANE","PLANT","POINT","POWER","PRESS","PRICE","PRIDE","PRIME","PRINT","PRIZE",
        "PROOF","PROUD","PROVE","QUEEN","QUEST","QUICK","QUIET","QUITE","QUOTE","RADIO",
        "RAISE","RANGE","REACH","READY","REFER","RIVER","ROBOT","ROUGH","ROUND","ROUTE",
        "ROYAL","SCALE","SCENE","SCORE","SHAPE","SHARE","SHARP","SHEET","SHELL","SHIFT",
        "SHINE","SHIRT","SHOCK","SHOOT","SHORT","SHOUT","SIGHT","SINCE","SLEEP","SMART",
        "SMILE","SMOKE","SNAKE","SOLID","SOLVE","SOUND","SOUTH","SPACE","SPARE","SPARK",
        "SPEAK","SPEED","SPELL","SPEND","SPICE","SPLIT","SPORT","STAGE","STAND","START",
        "STATE","STEAM","STEEL","STICK","STILL","STOCK","STONE","STORE","STORM","STORY",
        "STUDY","STUFF","STYLE","SUGAR","SWEET","SWING","SWORD","TABLE","TASTE","TEACH",
        "THANK","THEIR","THEME","THERE","THESE","THICK","THING","THINK","THIRD","THOSE",
        "THREE","THROW","TIGER","TIGHT","TODAY","TOTAL","TOUCH","TOUGH","TOWER","TRACE",
        "TRACK","TRADE","TRAIN","TREAT","TREND","TRIAL","TRIBE","TRICK","TRUCK","TRULY",
        "TRUST","TRUTH","TWICE","UNCLE","UNDER","UNION","UNTIL","UPPER","URBAN","VALUE",
        "VIDEO","VITAL","VOICE","WATCH","WATER","WHALE","WHEAT","WHEEL","WHERE","WHICH",
        "WHILE","WHITE","WHOLE","WHOSE","WIDTH","WOMAN","WORLD","WORRY","WORTH","WOULD",
        "WRITE","WRONG","YOUNG","YOUTH"
    };

    // ===== Game state =====
    static final int MAX_GUESSES = 6;
    static final int WORD_LEN = 5;

    public static void main(String[] args) {
        String target = WORDS[new Random().nextInt(WORDS.length)];
        Set<String> dict = new HashSet<>(Arrays.asList(WORDS));
        Scanner scanner = new Scanner(System.in);

        printIntro();

        int guessesMade = 0;
        boolean won = false;

        while (guessesMade < MAX_GUESSES && !won) {
            System.out.print("Guess " + (guessesMade + 1) + "/" + MAX_GUESSES + " > ");
            if (!scanner.hasNextLine()) break;
            String guess = scanner.nextLine().trim().toUpperCase();

            if (guess.length() != WORD_LEN) {
                System.out.println("  Must be exactly " + WORD_LEN + " letters.");
                continue;
            }
            if (!guess.chars().allMatch(Character::isLetter)) {
                System.out.println("  Letters only, please.");
                continue;
            }
            if (!dict.contains(guess)) {
                System.out.println("  '" + guess + "' isn't in the word list.");
                continue;
            }

            String[] colors = judge(guess, target);
            renderRow(guess, colors);

            if (guess.equals(target)) won = true;
            guessesMade++;
        }

        if (won) {
            System.out.println("\n" + BOLD + "🎉 Got it in " + guessesMade
                    + (guessesMade == 1 ? " try!" : " tries!") + RESET);
        } else {
            System.out.println("\n" + BOLD + "💀 Out of guesses. The word was: "
                    + target + RESET);
        }
    }

    /**
     * Returns an ANSI background code for each letter of the guess.
     * Handles duplicate letters correctly using a two-pass algorithm:
     * first marks greens, then marks yellows from what's left.
     */
    static String[] judge(String guess, String target) {
        String[] out = new String[WORD_LEN];
        char[] t = target.toCharArray();

        // Pass 1: greens
        for (int i = 0; i < WORD_LEN; i++) {
            if (guess.charAt(i) == t[i]) {
                out[i] = BG_GREEN;
                t[i] = '_';  // consume — can't be matched again
            }
        }
        // Pass 2: yellows and grays
        for (int i = 0; i < WORD_LEN; i++) {
            if (out[i] != null) continue;
            int idx = indexOf(t, guess.charAt(i));
            if (idx >= 0) {
                out[i] = BG_YELLOW;
                t[idx] = '_';
            } else {
                out[i] = BG_GRAY;
            }
        }
        return out;
    }

    static int indexOf(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) if (arr[i] == c) return i;
        return -1;
    }

    static void renderRow(String guess, String[] colors) {
        StringBuilder sb = new StringBuilder("           ");
        for (int i = 0; i < guess.length(); i++) {
            sb.append(colors[i]).append(" ").append(guess.charAt(i)).append(" ").append(RESET).append(" ");
        }
        System.out.println(sb);
    }

    static void printIntro() {
        System.out.println(BOLD + "═══════════════════════════════" + RESET);
        System.out.println(BOLD + "    WORDLE — Terminal Edition" + RESET);
        System.out.println(BOLD + "═══════════════════════════════" + RESET);
        System.out.println("Guess the 5-letter word in " + MAX_GUESSES + " tries.");
        System.out.println(BG_GREEN  + " G " + RESET + " = right letter, right spot");
        System.out.println(BG_YELLOW + " Y " + RESET + " = right letter, wrong spot");
        System.out.println(BG_GRAY   + " - " + RESET + " = not in word\n");
    }
}
