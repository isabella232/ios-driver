/*
 * Copyright 2012 ios-driver committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uiautomation.ios.utils;

import com.dd.plist.BinaryPropertyListParser;
import com.dd.plist.NSObject;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.BeanToJsonConverter;
import org.uiautomation.ios.server.application.LanguageDictionary;
import org.uiautomation.iosdriver.ApplicationInfo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class PlistFileUtils {

  private final File source;
  private final Map<String, Object> plistContent;

  public PlistFileUtils(File source) {
    this.source = source;
//    this.plistContent = read(source);
    if(source.getPath().endsWith("es.lproj/Localizable.strings") || source.getPath().endsWith("en.lproj/Localizable.strings")){
        this.plistContent = readByXoom(source); //Modified by Xoom
    }else{
        this.plistContent = read(source);
    }
  }


  private Map<String, Object> read(File bplist) {
    NSObject object = null;
    try {
      object = BinaryPropertyListParser.parse(bplist);
    } catch (Exception e) {
      throw new WebDriverException("Cannot parse info.plist : " + e.getMessage(), e);
    }
    ApplicationInfo info = new ApplicationInfo(object);
    return info.getProperties();
  }

  public JSONObject toJSON() throws Exception {
    JSONObject res = new JSONObject(plistContent);
    return res;
  }

    /**
     * Added and used by Xoom for processing non-binary Localization.strings files.
     * */
    private Map<String, Object> readByXoom(File file){
        Map<String, Object> fileContentMap=new HashMap<String, Object>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                this.processOneLineByXoom(line, fileContentMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileContentMap;
    }

    private void processOneLineByXoom(String oneLine, Map<String, Object> fileContentMap){
        String key = null, value = null;
        StringTokenizer st=new StringTokenizer(oneLine, "\"");

        int i=0;
        while(st.hasMoreTokens()){
            i++;
            if(i==1){
                key=st.nextToken();
            }else if(i==3){
                value=st.nextToken();
            }else{
                st.nextToken();
            }
        }

        if(key==null || value==null){
            throw new RuntimeException("Error proccessing Localization.strings file.");
        }

        fileContentMap.put(key, value);
    }
}
