package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Feed;

public interface FeedRepository {

	public List<Feed> selectFeeds(int filter);
	public Integer save(Feed feed);
	public void deleteAllFeedsFromDocument(int idDocumentoDetalhes);
}
