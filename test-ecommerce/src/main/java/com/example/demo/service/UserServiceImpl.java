package com.example.demo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.model.User;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.UserRequest;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;

@Service
public class UserServiceImpl implements UserService{

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseObj update(User user) {
		try {
			Long id = user.getId();
			if (id != null) {
				Optional<User> optional = this.userRepository.findById(id);
				if (optional.isPresent()) {
					user.setUsername(optional.get().getUsername());
					user.setPassword(optional.get().getPassword());
					this.userRepository.save(user);
					return new ResponseObj(Constants.SUCCESSCODE, Constants.CAPNHATTHANHCONG);
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.IDNOTFOUND);
				}

			} else {
				return new ResponseObj(Constants.ERRORCODE, Constants.NEDUPDATE);
			}
		} catch (Exception e) {
			String errorMessage = "Lỗi chưa xác định!";
			if (e instanceof DataIntegrityViolationException) {
				Throwable cause = e.getCause();
				while ((cause != null) && !(cause instanceof SQLException)) {
					cause = cause.getCause();
				}
				if (cause instanceof SQLException) {
					int errorCode = ((SQLException) cause).getErrorCode();
					switch (errorCode) {
					case 1062:
						errorMessage = "Lỗi trùng khóa: Mã sản phẩm đã tồn tại: " + cause.getMessage();
						break;
					case 1452:
						errorMessage = "Lỗi khóa ngoại: Không tìm thấy khóa ngoại tương ứng: " + cause.getMessage();
						break;
					default:
						errorMessage = "Lỗi SQL: " + cause.getMessage();
						break;
					}
				}
			} else if (e instanceof TransactionSystemException) {
				Throwable cause = e.getCause();
				if (cause instanceof RollbackException) {
					Throwable rootCause = cause.getCause();
					if (rootCause instanceof ConstraintViolationException) {
						errorMessage = "Lỗi ràng buộc: " + rootCause.getMessage();
					}
				}
			} else if (e instanceof OptimisticLockException) {
				errorMessage = "Lỗi đồng bộ hóa: Bản ghi đã bị thay đổi bởi người dùng khác!";
			} else {
				errorMessage = "Lỗi không xác định: " + e.getMessage();
			}
			return new ResponseObj(Constants.ERRORCODE, Constants.CAPNHATTHATBAI, errorMessage);
		}
	}

	@Override
	public ResponseObj status(Long id) {
		try {
			if (id != null) {
				Optional<User> optional = this.userRepository.findById(id);
				if (optional.isPresent()) {
					System.err.println(optional.get().getUsername());
					User us = optional.get();
					us.setDeleted("Y");
					this.userRepository.save(us);
					return new ResponseObj(Constants.SUCCESSCODE, Constants.CAPNHATTHANHCONG);
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.IDNOTFOUND);
				}

			} else {
				return new ResponseObj(Constants.ERRORCODE, Constants.NEDUPDATE);
			}
		} catch (Exception e) {
			String errorMessage = "Lỗi chưa xác định!";
			if (e instanceof DataIntegrityViolationException) {
				Throwable cause = e.getCause();
				while ((cause != null) && !(cause instanceof SQLException)) {
					cause = cause.getCause();
				}
				if (cause instanceof SQLException) {
					int errorCode = ((SQLException) cause).getErrorCode();
					switch (errorCode) {
					case 1062:
						errorMessage = "Lỗi trùng khóa: Mã sản phẩm đã tồn tại: " + cause.getMessage();
						break;
					case 1452:
						errorMessage = "Lỗi khóa ngoại: Không tìm thấy khóa ngoại tương ứng: " + cause.getMessage();
						break;
					default:
						errorMessage = "Lỗi SQL: " + cause.getMessage();
						break;
					}
				}
			} else if (e instanceof TransactionSystemException) {
				Throwable cause = e.getCause();
				if (cause instanceof RollbackException) {
					Throwable rootCause = cause.getCause();
					if (rootCause instanceof ConstraintViolationException) {
						errorMessage = "Lỗi ràng buộc: " + rootCause.getMessage();
					}
				}
			} else if (e instanceof OptimisticLockException) {
				errorMessage = "Lỗi đồng bộ hóa: Bản ghi đã bị thay đổi bởi người dùng khác!";
			} else {
				errorMessage = "Lỗi không xác định: " + e.getMessage();
			}
			return new ResponseObj(Constants.ERRORCODE, Constants.CAPNHATTHATBAI, errorMessage);
		}
	}


	@Override
	public ResponseObj search(BaseRequest baseRequest) {
		try {
			Object customize = baseRequest.getCustomize();
			ObjectMapper mapper = new ObjectMapper();

			UserRequest user = mapper.convertValue(customize, new TypeReference<UserRequest>() {
			});

			if (baseRequest.getPageNumber() == null || baseRequest.getPageNumber() == 0) {
				baseRequest.setPageNumber(1);
			}
			if (baseRequest.getPageSize() == null || baseRequest.getPageSize() == 0) {
				baseRequest.setPageNumber(10);
			}

			Pageable pageable = PageRequest.of(baseRequest.getPageNumber() - 1, baseRequest.getPageSize(),
					Sort.by(Order.desc("id")));

			Specification<User> specification = new Specification<User>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {

					List<Predicate> predicates = new ArrayList<>();

					if (Utils.CheckNullString(user.getQ())) {
						predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("username")),
								Utils.likeText(user.getQ())));
	
						return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
					}

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

				}
			};
			Page<User> page = this.userRepository.findAll(specification, pageable);
			return new ResponseObj(Constants.SUCCESSCODE, Constants.OK, page);
		} catch (Exception e) {
			return new ResponseObj(Constants.ERRORCODE, Constants.ERROR, e.getMessage());
		}

	}

	@Override
	public ResponseObj getById(Long id) {
		try {
			if (id != null) {
				Optional<User> optional = this.userRepository.findById(id);
				if (optional.isPresent()) {
					return new ResponseObj(Constants.SUCCESSCODE, Constants.OK, optional.get());
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.IDNOTFOUND);
				}

			} else {
				return new ResponseObj(Constants.ERRORCODE, Constants.NEDUPDATE);
			}
		} catch (Exception e) {
			return new ResponseObj(Constants.ERRORCODE, Constants.ERROR, e.getMessage());
		}
	}

}
