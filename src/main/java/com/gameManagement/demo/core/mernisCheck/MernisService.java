package com.gameManagement.demo.core.mernisCheck;

import com.gameManagement.demo.core.results.Result;

public interface MernisService {

    Result checkIfPlayerIsValid(long nationalityId,String firstName,String lastName,int birthOfYear) throws Exception;
}
