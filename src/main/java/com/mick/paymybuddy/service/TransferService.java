package com.mick.paymybuddy.service;

import java.util.List;

import com.mick.paymybuddy.dto.InternalTransferDto;

public interface TransferService {

	InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto);

   // ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto);

    List<InternalTransferDto> findInternalTransferByUser(String emailOwner);

    //List<ExternalTransferDto> findExternalTransferByUser(String username);

}
