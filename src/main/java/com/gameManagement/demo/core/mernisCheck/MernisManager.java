package com.gameManagement.demo.core.mernisCheck;

import com.gameManagement.demo.core.results.ErrorResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.mernis.RMRKPSPublicSoap12;
import org.springframework.stereotype.Service;


@Service
public class MernisManager implements MernisService{
    @Override
    public Result checkIfPlayerIsValid(long nationalityId, String firstName, String lastName, int birthOfYear) throws Exception {

        RMRKPSPublicSoap12 rmrkpsPublicSoap12 = new RMRKPSPublicSoap12();

        if (rmrkpsPublicSoap12.TCKimlikNoDogrula(nationalityId,firstName,lastName,birthOfYear)){
            return new SuccessResult("Valid Player");
        }

        return new ErrorResult("Player is not Valid");
    }
}
