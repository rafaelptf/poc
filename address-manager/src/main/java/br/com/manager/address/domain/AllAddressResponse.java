package br.com.manager.address.domain;

import br.com.manager.common.domain.BaseResponse;

import java.util.List;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class AllAddressResponse extends BaseResponse {

    private final List<CompleteAddress> completeAddresses;

    public AllAddressResponse(BaseResponse baseResponse, List<CompleteAddress> completeAddresses) {
        super(baseResponse);
        this.completeAddresses = completeAddresses;
    }

    public List<CompleteAddress> getCompleteAddresses() {
        return completeAddresses;
    }
}
