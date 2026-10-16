package newpackage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class audios {
    private MediaPlayer homeaudiomp;
    private MediaView homeaudiomv;
    
    private MediaPlayer gameaudiomp;
    private MediaView gameaudiomv;
    
    private MediaPlayer sohmp;
    private MediaView sohmv;
    
    private MediaPlayer successmp;
    private MediaView successmv;
    
    private MediaPlayer failmp;
    private MediaView failmv;
    
    private MediaPlayer copybookmp;
    private MediaView copybookmv;
    
    private MediaPlayer tabsmp;
    private MediaView tabsmv;
    
    private MediaPlayer snmp;
    private MediaView snmv;
    
    private MediaPlayer computermp;
    private MediaView computermv;
    
    private MediaPlayer diemp;
    private MediaView diemv;
    
    private MediaPlayer debugmp;
    private MediaView debugmv;
    
    
    private MediaPlayer victorymp;
    private MediaView victorymv;
    
    private MediaPlayer newhourmp;
    private MediaView newhourmv;
    
    private MediaPlayer finalhourmp;
    private MediaView finalhourmv;
    
    private MediaPlayer codeSabotagemp;
    private MediaView codeSabotagemv;
    
    private MediaPlayer lightsOffmp;
    private MediaView lightsOffmv;
    
    private MediaPlayer wiresmp;
    private MediaView wiresmv;
    
    private MediaPlayer camerasGlitchmp;
    private MediaView camerasGlitchmv;
    
    private MediaPlayer clickmp;
    private MediaView clickmv;
    
    private MediaPlayer overheatmp;
    private MediaView overheatmv;
    
    
    public audios(){
        Media homeAudio = new Media("file:///Users/dell/Downloads/THIShomepageaudio.mp3");
        homeaudiomp = new MediaPlayer(homeAudio);
        homeaudiomv = new MediaView(homeaudiomp);
        
        Media gameAudio = new Media("file:///Users/dell/Downloads/gameplayaudio.mp3");
        gameaudiomp = new MediaPlayer(gameAudio);
        gameaudiomv = new MediaView(gameaudiomp);
        
        Media systemoverheatalarm = new Media("file:///Users/dell/Downloads/systemoverheatalarm.mp3");
        sohmp = new MediaPlayer(systemoverheatalarm);
        sohmv = new MediaView(sohmp);
        
        Media success = new Media("file:///Users/dell/Downloads/success.mp3");
        successmp = new MediaPlayer(success);
        successmv = new MediaView(successmp);
        
        Media fail = new Media("file:///Users/dell/Downloads/jumpScare.mp3");
        failmp = new MediaPlayer(fail);
        failmv = new MediaView(failmp);
        
        Media copybook = new Media("file:///Users/dell/Downloads/openingcopybooksound.mp3");
        copybookmp = new MediaPlayer(copybook);
        copybookmv = new MediaView(copybookmp);
        
        Media tabs = new Media("file:///Users/dell/Downloads/switchingtabssound.mp3");
        tabsmp = new MediaPlayer(tabs);
        tabsmv = new MediaView(tabsmp);
        
        Media sn = new Media("file:///Users/dell/Downloads/stickynoteaudio.mp3");
        snmp = new MediaPlayer(sn);
        snmv = new MediaView(snmp);
        
        Media computer = new Media("file:///Users/dell/Downloads/pressingcomputeraudio.mp3");
        computermp = new MediaPlayer(computer);
        computermv = new MediaView(computermp);
        
        Media die = new Media("file:///Users/dell/Downloads/timeSabotage.mp3");
        diemp = new MediaPlayer(die);
        diemv = new MediaView(diemp);
        
        Media debug = new Media("file:///Users/dell/Downloads/debugTask.mp3");
        debugmp = new MediaPlayer(debug);
        debugmv = new MediaView(debugmp);
        
        
        Media victory = new Media("file:///Users/dell/Downloads/victory.mp3");
        victorymp = new MediaPlayer(victory);
        victorymv = new MediaView(victorymp);
        
        Media newhour = new Media("file:///Users/dell/Downloads/newHour.mp3");
        newhourmp = new MediaPlayer(newhour);
        newhourmv = new MediaView(newhourmp);
        
        Media finalhour = new Media("file:///Users/dell/Downloads/finalHour.mp3");
        finalhourmp = new MediaPlayer(finalhour);
        finalhourmv = new MediaView(finalhourmp);
        
        Media codeSabotage = new Media("file:///Users/dell/Downloads/codeSabotage.mp3");
        codeSabotagemp = new MediaPlayer(codeSabotage);
        codeSabotagemv = new MediaView(codeSabotagemp);
        
        Media lightsOff = new Media("file:///Users/dell/Downloads/lightsOff.mp3");
        lightsOffmp = new MediaPlayer(lightsOff);
        lightsOffmv = new MediaView(lightsOffmp);
        
        Media wires = new Media("file:///Users/dell/Downloads/connectingwiresound.mp3");
        wiresmp = new MediaPlayer(wires);
        wiresmv = new MediaView(wiresmp);
        
        Media camerasGlitch = new Media("file:///Users/dell/Downloads/camerasGlitch.mp3");
        camerasGlitchmp = new MediaPlayer(camerasGlitch);
        camerasGlitchmv = new MediaView(camerasGlitchmp);
        
        Media click = new Media("file:///Users/dell/Downloads/buttonClick.mp3");
        clickmp = new MediaPlayer(click);
        clickmv = new MediaView(clickmp);
        
        Media overheat = new Media("file:///Users/dell/Downloads/systemoverheat.mp3");
        overheatmp = new MediaPlayer(overheat);
        overheatmv = new MediaView(overheatmp);
        
    }
    
    public MediaPlayer getHomeAudioMP(){
        return homeaudiomp;
    }
    
    public MediaView getHomeAudioMV(){
        return homeaudiomv;
    }
    
    public MediaPlayer getGameAudioMP(){
        return gameaudiomp;
    }
    
    public MediaView getGameAudioMV(){
        return gameaudiomv;
    }
    
    public MediaPlayer getsohMP(){
        return sohmp;
    }
    
    public MediaView getsohMV(){
        return sohmv;
    }
    
    public MediaPlayer getsuccessMP(){
        return successmp;
    }
    
    public MediaView getsuccessMV(){
        return successmv;
    }
    
    public MediaPlayer getfailMP(){
        return failmp;
    }
    
    public MediaView getfailMV(){
        return failmv;
    }
    
    public MediaPlayer getcopybookMP(){
        return copybookmp;
    }
    
    public MediaView getcopybookMV(){
        return copybookmv;
    }
    
    public MediaPlayer gettabsMP(){
        return tabsmp;
    }
    
    public MediaView gettabsMV(){
        return tabsmv;
    }
    
    public MediaPlayer getsnMP(){
        return snmp;
    }
    
    public MediaView getsnMV(){
        return snmv;
    }
    
    public MediaPlayer getcomputerMP(){
        return computermp;
    }
    
    public MediaView getcomputerMV(){
        return computermv;
    }
    
    public MediaPlayer getdieMP(){
        return diemp;
    }
    
    public MediaView getdieMV(){
        return diemv;
    }
    
    public MediaPlayer getdebugMP(){
        return debugmp;
    }
    
    public MediaView getdebugMV(){
        return debugmv;
    }
    
    public MediaPlayer getvictoryMP(){
        return victorymp;
    }
    
    public MediaView getvictoryMV(){
        return victorymv;
    }
    
    public MediaPlayer getnewhourMP(){
        return newhourmp;
    }
    
    public MediaView getnewhourMV(){
        return newhourmv;
    }
    
    public MediaPlayer getfinalhourMP(){
        return finalhourmp;
    }
    
    public MediaView getfinalhourMV(){
        return finalhourmv;
    }
    
    public MediaPlayer getcodeSabotageMP(){
        return codeSabotagemp;
    }
    
    public MediaView getcodeSabotageMV(){
        return codeSabotagemv;
    }
    
    public MediaPlayer getlightsOffMP(){
        return lightsOffmp;
    }
    
    public MediaView getlightsOffMV(){
        return lightsOffmv;
    }
    
    public MediaPlayer getwiresMP(){
        return wiresmp;
    }
    
    public MediaView getwiresMV(){
        return wiresmv;
    }
    
    public MediaPlayer getcamerasGlitchMP(){
        return camerasGlitchmp;
    }
    
    public MediaView getcamerasGlitchMV(){
        return camerasGlitchmv;
    }
    
    public MediaPlayer getclickMP(){
        return clickmp;
    }
    
    public MediaView getclickMV(){
        return clickmv;
    }
    
    public MediaPlayer getoverheatMP(){
        return overheatmp;
    }
    
    public MediaView getoverheatMV(){
        return overheatmv;
    }
    
    
}


