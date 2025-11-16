package app.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcess(String message) {
        return message.toUpperCase();
    }

}
