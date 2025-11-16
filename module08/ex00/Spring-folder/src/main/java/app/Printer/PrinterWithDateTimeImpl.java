package app.Printer;


import app.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String message) {
        renderer.render("[" + java.time.LocalDateTime.now() + "] " + message);
    }
}
