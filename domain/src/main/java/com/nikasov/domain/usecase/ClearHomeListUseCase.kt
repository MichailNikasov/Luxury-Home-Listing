package com.nikasov.domain.usecase

import com.nikasov.domain.repository.HomeRepository
import javax.inject.Inject

class ClearHomeListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() {
        return homeRepository.clear()
    }
}