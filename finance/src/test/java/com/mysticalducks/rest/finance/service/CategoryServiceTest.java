package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.IconNotFoundException;
import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceTest {
	
	@InjectMocks
	CategoryService service;

	@Mock
	CategoryRepository categoryRepository;
	
	@Mock
	PartyService partyService;
	
	@Mock
	IconService iconService;

	Category category;
	Party party;
	Icon icon;

	@BeforeEach
	void setUp() {
		this.icon = new Icon("Icon");
		this.party = new Party("Party", new FinanceInformation());
		this.category = new Category("Category", party , icon);
	}

	@Test
	void deleteById() {
		service.deleteById(5);

		verify(categoryRepository).deleteById(5);
	}
	
	
    @Test
    public void findAllByPartyIdTest() {
        Party party = new Party();
        party.setId(1);
        List<Category> categories = List.of(new Category("Category1", party, new Icon("Icon1")));

        when(partyService.findById(1)).thenReturn(party);
        when(categoryRepository.findAllCategoriesByPartyId(party)).thenReturn(categories);
  
        List<Category> result = service.findAllByPartyId(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Category1");
    }
    
    
    @Test
    void findAllByPartyId_partyNotFoundException() {
        when(partyService.findById(1)).thenReturn(null);

        PartyNotFoundException thrown = assertThrows(PartyNotFoundException.class, 
            () -> service.findAllByPartyId(1));

        assertThat(thrown.getMessage()).isEqualTo("Party not found with id 1");
    }
    
	@Test
	void findById() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

		Category foundCategory = service.findById(1);

		assertThat(foundCategory).isNotNull();

		verify(categoryRepository).findById(1);
		
		CategoryNotFoundException thrown = assertThrows(CategoryNotFoundException.class, () -> service.findById(2),
				"No data found for the id 2");

		assertTrue(thrown.getMessage().contains("2"));

	}

	@Test
	void save() {
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		
		when(partyService.findById(party.getId())).thenReturn(party);
		when(iconService.findById(icon.getId())).thenReturn(icon);
		
		Category savedCategory = service.save(party.getId(), "Category", icon.getId());

		verify(categoryRepository).save(any(Category.class));

		assertThat(savedCategory).isNotNull();
	}

	
	@Test
	void save_partyIsNull() {
		assertThrows(PartyNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(null);

			service.save(party.getId(), "Party", icon.getId());
			}
		);
	}

	@Test
	void save_iconIsNull() {
		assertThrows(IconNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(party);
			when(iconService.findById(icon.getId())).thenReturn(null);
			
			service.save(party.getId(), "Party", icon.getId());
			}
		);
	}
	
	@Test
	void findAll() {
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		when(categoryRepository.findAll()).thenReturn(categories);

		List<Category> foundCategories = service.findAll();

		verify(categoryRepository).findAll();

		assertThat(foundCategories).hasSize(1);
	}

	@Test
	void replace() {
		when(categoryRepository.findById(0)).thenReturn(Optional.of(category));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		Category replacedCategory = service.replace(category);

		verify(categoryRepository, times(1)).findById(0);
		verify(categoryRepository, times(1)).save(any(Category.class));

		assertThat(replacedCategory).isNotNull();
	}
	
	@Test
	void replaceNew() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		
		Category newCategory = new Category("NewCategorie", party, icon);
		newCategory.setID(1);
		Category replacedCategory = service.replace(newCategory);

		verify(categoryRepository, times(1)).findById(1);
		verify(categoryRepository, times(1)).save(any(Category.class));
		
		assertThat(replacedCategory).isNotNull();
		
	}
	
}
