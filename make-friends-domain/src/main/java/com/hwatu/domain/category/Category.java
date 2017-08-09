package com.hwatu.domain.category;

import com.hwatu.domain.gathering.Gathering;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwatu on 2017. 3. 6..
 */

@Data
@Entity
@Table(name = "categories")
public class Category {
	@Id
	@Column(name = "categoryId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@Column(name = "categoryName")
	private String categoryName;

	@OneToMany(mappedBy = "category")
	private List<Gathering> gatheringList = new ArrayList<>();
}
