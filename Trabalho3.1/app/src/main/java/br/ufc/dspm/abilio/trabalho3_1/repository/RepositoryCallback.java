package br.ufc.dspm.abilio.trabalho3_1.repository;

import br.ufc.dspm.abilio.trabalho3_1.model.Result;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
