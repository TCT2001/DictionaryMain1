package speech;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

//using jsapi
public class Speech {

    private static SynthesizerModeDesc desc;
    private static Synthesizer synthesizer;
    private static Voice voice;


    private static void init(String voiceName)
            throws EngineException, AudioException, EngineStateError,
            PropertyVetoException {
        if (desc == null) {

            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            desc = new SynthesizerModeDesc(Locale.US);
            Central.registerEngineCentral
                    ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
            SynthesizerModeDesc smd =
                    (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
            Voice[] voices = smd.getVoices();
            Voice voice = null;
            for (int i = 0; i < voices.length; i++) {
                if (voices[i].getName().equals(voiceName)) {
                    voice = voices[i];
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        }

    }

    private static void terminate() throws EngineException, EngineStateError {
        synthesizer.deallocate();
    }
    //call funtion to speech english :)
    public static void doSpeak(String speakText)
            throws EngineException, AudioException, IllegalArgumentException,
            InterruptedException, PropertyVetoException {
        init("kevin");
        synthesizer.speakPlainText(speakText, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        terminate();
    }
}