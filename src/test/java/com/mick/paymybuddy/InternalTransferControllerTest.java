package com.mick.paymybuddy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import com.mick.paymybuddy.controller.InternalTransferController;
import com.mick.paymybuddy.dto.InternalTransferDto;
import com.mick.paymybuddy.exception.DataNotFoundException;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.service.TransferService;
import com.mick.paymybuddy.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class InternalTransferControllerTest {
	  @Autowired
	    InternalTransferController internalTransferController;
	    @Mock
	    private TransferService transferService;
	    @Mock
	    private UserService userService;
	    @Mock
	    UserDetails userDetails;
	    @Mock
	    RedirectAttributes redirectAttributes;
	    @Mock
	    Model model;
	    @Mock
	    InternalTransferDto internalTransferDto;

	    @Test
	    void transferPage() {
	        when(userService.listEmailRelation("email1")).thenReturn(Arrays.asList(new Relation()));
	        when(transferService.findInternalTransferByUser("email1")).thenReturn(Arrays.asList(new InternalTransferDto()));
	        String result = internalTransferController.internalTransferPage(model, userDetails);
	        Assertions.assertThat(result).isEqualTo("internalTransfer");
	    }

//	    @Test
//	    void internalTransfer() {
//	        when(transferService.doInternalTransfer(internalTransferDto)).thenReturn(internalTransferDto);
//	        assertThrows(DataNotFoundException.class, () -> internalTransferController.doInternalTransfer(internalTransferDto, userDetails, redirectAttributes));
//	    }
}
