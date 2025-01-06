package com.nikasov.domain.usecase

import com.nikasov.domain.repository.HomeRepository
import javax.inject.Inject

class ToggleFavoriteHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(homeId: Int) = homeRepository.toggleFavorite(homeId)
}