import java.util.*;

public class Bingo {

    //private fields
    private final int gameMode;

    private final Player[] players;

    private final int numberOfPlayer;
    private final int numberOfCards;

    private final HashMap<Integer, String> bingoIndex = new HashMap<>();

    private LinkedList<Integer> rolledNumbers = new LinkedList<>();

    private HashMap<Player, LinkedList<BingoCard>> completedCards = new HashMap<>();


    Bingo(int numberOfPlayer, int numberOfCards, int gameMode){
        this.numberOfCards = numberOfCards;
        this.numberOfPlayer = numberOfPlayer;

        players = new Player[numberOfPlayer];

        for(int i = 0; i < numberOfPlayer; i++)
            players[i] = new Player("Player " + (i+1), numberOfCards);

        bingoIndex.put(1, "B");
        bingoIndex.put(2, "I");
        bingoIndex.put(3, "N");
        bingoIndex.put(4, "G");
        bingoIndex.put(5, "O");

        this.gameMode = gameMode;
    }

    // public methods

    public void start(){
        showAllPlayersCards();

        Scanner input = new Scanner(System.in);

        do {
            System.out.print("\nPress Enter To Roll");
            input.nextLine();

            System.out.println();

            int[] roll = roll();

            printRoll(bingoIndex.get(roll[0]), roll[1]);

            markAndCheckCards(roll[0]-1, String.valueOf(roll[1]));

            showAllPlayersCards();
        } while (!hasWinners());

        printWinners();

        updateScores();
        printScores();
        resetCards();

        start();
    }

    public void resetCards(){
        rolledNumbers = new LinkedList<>();
        completedCards = new HashMap<>();

        for(Player player : players)
            player.resetPlayerCards();
    }

    public void markAndCheckCards(int column, String currentRoll){
        LinkedList<BingoCard> winningCards;

        for(int i = 0; i < numberOfPlayer; i++){ //players
            players[i].markCards(column, currentRoll);

            winningCards = players[i].getCompletedCards(i, column, gameMode);

            if(winningCards != null)
                completedCards.put(players[i], winningCards);
        }
    }

    public boolean hasWinners(){
        return completedCards.size() > 0;
    }

    public void printWinners(){ // private
        int count = 1;

        System.out.println("\nWinners: ");

        for (Map.Entry<Player, LinkedList<BingoCard>> entry : completedCards.entrySet()) {
            String name = entry.getKey().getName();
            LinkedList<BingoCard> value = entry.getValue();

            System.out.print("" + count + ". " + name + " with cards number ");
            String[] cardNumbers = new String[value.size()];

            for(int i = 0; i < value.size(); i++)
                cardNumbers[i] = String.valueOf(value.get(i).cardNumber);

            System.out.println(String.join("and ", cardNumbers));

            count += 1;
        }
    } // private

    public int[] roll(){
        int randomColumn = 0;
        int randomNumberInColumn = 0;

        while(randomNumberInColumn == 0 || rolledNumbers.contains(randomNumberInColumn)){
            randomColumn = (int)((Math.random() * 5) + 1);

            int max = randomColumn * 15;
            int min = max - 14;
            int range = max - min + 1;

            randomNumberInColumn = (int)((Math.random() * range) + min);
        }

        rolledNumbers.add(randomNumberInColumn);

        return new int[] {randomColumn, randomNumberInColumn};
    } // should be private

    public void showAllPlayersCards(){
        System.out.println();

        for(int i = 0; i < numberOfPlayer; i++){ //players
            System.out.println(players[i].getName() + ": ");

            for(int z = 0; z < numberOfCards; z++){
                if(z == 0) System.out.print("                      ");
                System.out.print("Card Number " + players[i].getCards().get(z).getCardNumber() + "                                              ");
            }

            System.out.println();

            for(int x = 0; x < numberOfCards; x++)
                System.out.print("         B        I         N         G         O         ");

            System.out.println();

            for(int j = 0; j < 5; j++){ // row
                for(int k = 0; k < numberOfCards; k++){ //cards
                    for(int l = 0; l < 5; l++){ // column
                        String number = players[i].getCards().get(k).getBingoCard()[j][l];
                        System.out.printf("%1$"+10+ "s", number);
                    }

                    System.out.print("        ");
                }

                System.out.println();
            }
            System.out.println("\n\n");
        }


    }

    public void printScores(){
        System.out.println("\nScores: ");

        for(int i = 0; i < numberOfPlayer; i++){
            String name = players[i].getName();
            int score = players[i].getScore();

            System.out.println(name + ": " + score);
        }
    }

    // private methods

    private void updateScores(){
        for (Map.Entry<Player, LinkedList<BingoCard>> entry : completedCards.entrySet()) {
            int completedCardsNumber = entry.getValue().size();
            Player player = entry.getKey();

            player.score += completedCardsNumber;
        }
    }

    private void printRoll(String randomColumn, int randomNumberInColumn){
        printDots();

        System.out.print(" in " + randomColumn + " ");

        printDots();

        System.out.print(" number " + randomNumberInColumn + "!" + "\n");

        sleep(1000);
    }

    private void printDots(){
        for(int i = 0; i < 3; i++){
            System.out.print(".");
            sleep(500);
        }
    }

    private void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // static inner classes

    private static class Player{
        private final String name;
        private int score = 0;

        private ArrayList<BingoCard> cards = new ArrayList<>();

        Player(String name, int numberOfCards){
            this.name = name;

            for(int i = 0; i < numberOfCards; i++)
                cards.add(new BingoCard());
        }

        public void resetPlayerCards(){
            ArrayList<BingoCard> newCards = new ArrayList<>();

            for(int i = 0; i < cards.size(); i++)
                newCards.add(new BingoCard());

            cards = newCards;
        }

        public int getScore(){
            return this.score;
        }

        public ArrayList<BingoCard> getCards(){
            return cards;
        }

        public LinkedList<BingoCard> getCompletedCards(int row, int column, int gameMode){
            LinkedList<BingoCard> completedCardsOfCurrentPlayer = new LinkedList<>();

            for (BingoCard card : cards)
                if (card.checkCard(row, column, gameMode))
                    completedCardsOfCurrentPlayer.add(card);

            return completedCardsOfCurrentPlayer.size() > 0 ? completedCardsOfCurrentPlayer : null;
        }

        public void markCards(int column, String currentRoll){
            for (BingoCard bingoCard : cards) {
                for (int j = 0; j < 5; j++) {
                    String[][] card = bingoCard.getBingoCard();
                    String cellNum = card[j][column];

                    if (cellNum == null)
                        continue;

                    if (cellNum.equals(currentRoll)) {
                        card[j][column] = "✓"; // ✓ ✔ 🗸
                    }
                }
            }
        }

        private String getName(){
            return this.name;
        }
    }

    private static class BingoCard{

        private static int totalNumberOfCard = 0;

        private final int cardNumber;

        private final String[][] bingoCard = new String[5][5];

        BingoCard(){
            totalNumberOfCard += 1;
            this.cardNumber = totalNumberOfCard;
            fillUpCard();
        }

        public int getCardNumber(){
            return this.cardNumber;
        }

        public String[][] getBingoCard(){
            return bingoCard;
        }

        public boolean checkCard(int row, int column, int gameMode){
            if(gameMode == 1){

                boolean rowCheck = true;
                boolean colCheck = true;
                boolean slashCheck = true;
                boolean backSlashCheck = true;

                for(int i = 0; i < 5; i++){
                    try{
                        if(!bingoCard[row][i].equals("✓")) rowCheck = false;
                        if(!bingoCard[i][column].equals("✓")) colCheck = false;
                        if(!bingoCard[i][i].equals("✓")) slashCheck = false;
                        if(!bingoCard[4-i][i].equals("✓")) backSlashCheck = false;
                    }catch (NullPointerException ignored){
                    }
                }

                return rowCheck || colCheck || slashCheck || backSlashCheck;

            }else{
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        if(bingoCard[i][j] == null)
                            continue;
                        if(!bingoCard[i][j].equals("✓"))
                            return false;
                    }
                }

                return true;
            }
        }

        private void fillUpCard(){
            LinkedList<Integer> takenNumbers = new LinkedList<>();

            for(int i = 0, k = 0; k < 5; i += 15, k++){
                for(int j = 0; j < 5; j++){
                    if(i == 30 && j == 2)
                        continue;

                    int max = i + 15;
                    int min = i + 1;
                    int range = max - min + 1;

                    int randomNumber = (int)((Math.random() * range) + min);

                    if(takenNumbers.contains(randomNumber)){
                        j -= 1;
                        continue;
                    }

                    takenNumbers.add(randomNumber);

                    bingoCard[j][k] = Integer.toString(randomNumber);
                }
            }
        }
    }

    // main method

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.println("Game Modes: ");
        System.out.println("1. Linyahan\n2. Punuan");
        System.out.print("Select: ");
        int gameMode = input.nextInt();

        System.out.print("\nEnter Number of Player: ");
        int numberOfPlayer = input.nextInt();
        System.out.print("Enter Number of Cards: ");
        int numberOfCards = input.nextInt();

        Bingo game = new Bingo(numberOfPlayer, numberOfCards, gameMode);
        game.start();
    }
}
