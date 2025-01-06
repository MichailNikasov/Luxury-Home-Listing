package com.nikasov.domain.usecase

import com.nikasov.domain.entity.Home
import com.nikasov.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(start: Int, pageSize: Int): Result<List<Home>> {
        return homeRepository.getHomes(start, pageSize)
    }
}