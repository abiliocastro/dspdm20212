package br.ufc.dspm.abilio.drapp.repository;

import br.ufc.dspm.abilio.drapp.model.Result;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
