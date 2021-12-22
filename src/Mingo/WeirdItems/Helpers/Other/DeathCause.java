package Mingo.WeirdItems.Helpers.Other;

public enum DeathCause {

    HEART_ATTACK("Heart Attack", new String[]{"died of a heart attack."}, 0),
    LIGHTNING("Lightning Strike", new String[]{"got hit by a bit of lightning.", "got smitten by the gods.", "was struck by lightning."}, 1),
    EXPLODE("Explosion", new String[]{"exploded.", "blew up."}, 2);

    private String name; // maybe delete later
    private String[] messages;
    private int id;

    DeathCause(String name, String[] messages, int id) {
        this.name = name;
        this.messages = messages;
        this.id = id;
    }

    public String getMessage() { return this.messages[(int) (Math.random()*this.messages.length)]; }
    public int getId() { return this.id; }
}
