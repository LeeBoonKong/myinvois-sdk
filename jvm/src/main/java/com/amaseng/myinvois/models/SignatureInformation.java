/*
 * Copyright 2011-2024 AmaSeng Software Sdn. Bhd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amaseng.myinvois.models;

import lombok.Builder;

import java.util.*;

@Builder
public class SignatureInformation{
    private String id;
    private String referencedSignatureID;
    private UBLSignature signature;

    public SignatureInformation(String id, String referencedSignatureID, UBLSignature signature)
    {
        this.id = id;
        this.referencedSignatureID = referencedSignatureID;
        this.signature = signature;
    }

    public String getId()
    {
        return id;
    }

    public String getReferencedSignatureID()
    {
        return referencedSignatureID;
    }

    public UBLSignature getSignature()
    {
        return signature;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("ID", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", id); }}); }});
            put("ReferencedSignatureID", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", referencedSignatureID); }}); }});
            put("Signature", new ArrayList<Object>()  {{ add(signature.toMap()); }});
        }};
    }
}
