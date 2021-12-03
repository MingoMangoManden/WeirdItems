package Mingo.WeirdItems.DeathCauses;

public enum DeathCause {

    HEART_ATTACK("Heart Attack", new String[]{"died of a heart attack."}, 0),
    LIGHTNING("Lightning Strike", new String[]{"got hit by a bit of lightning.", "got smitten by the gods.", "was struck by lightning."}, 1),
    EATEN_BY_GROUND("Eaten By Ground", new String[]{"was eaten by the ground!", "was consumed by the ground!"}, 2),
    EXPLODE("Explosion", new String[]{"exploded.", "blew up."}, 3);

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
