package testcase;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;

import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc; 

import com.itextpdf.text.pdf.parser.Path;

public class sampleTestPDF {
	public static Synthesizer synthesizer;
	
	static void textToSpeech(String str) throws EngineException, AudioException, EngineStateError, IllegalArgumentException, InterruptedException
	{
		
		System.setProperty("freetts.voices", 
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  
                  
            // Register Engine 
            Central.registerEngineCentral 
                ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral"); 
  
            // Create a Synthesizer 
            Synthesizer synthesizer =                                          
                Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));      
      
            // Allocate synthesizer 
            synthesizer.allocate();         
              
            // Resume Synthesizer 
            synthesizer.resume();     
              
            // speaks the given text until queue is empty. 
            synthesizer.speakPlainText(str, null);          
           
	}
	static void exit() throws IllegalArgumentException, InterruptedException, EngineException, EngineStateError
	{
		 synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY); 
         
         // Deallocate the Synthesizer. 
         synthesizer.deallocate(); 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> mapMetaDataInfo = new HashMap();
		java.nio.file.Path path = new File("C:\\MTA_Final\\HRBO-MTA-Automation\\test-report\\TestReport_SignInNew_SC45_P1[1]_10-29-2018_11.14.57[PASS].pdf").toPath();
    	try{
    		final UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);    	   	 
    	    for (String attribute : view.list()) {
    	    	java.nio.ByteBuffer buffer = ByteBuffer.allocateDirect(view.size(attribute));
    	        view.read(attribute, buffer);
    	        buffer.flip();
    	        mapMetaDataInfo.put(attribute, StandardCharsets.UTF_8.decode(buffer).toString());
    	         	        	 
    	        	System.out.println(attribute+":"+mapMetaDataInfo.get(attribute));// StandardCharsets.UTF_8.decode(buffer).toString() returning null   
    	        	System.out.println(attribute+" is "+mapMetaDataInfo.get(attribute));
    	        	textToSpeech(attribute+" is "+mapMetaDataInfo.get(attribute));
    	    }    	
    	    exit();
    	   
    	}
    	catch(Exception e){
            System.out.println(e.getMessage());
        }
		
	}
	}


