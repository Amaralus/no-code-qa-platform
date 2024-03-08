package apps.amaralus.qa.platform.common;

import java.util.List;

public interface GenericMapper<D, M> {
    D mapToD(M m);

    M mapToM(D d);

    List<M> mapToListM(List<D> dList);

    List<D> mapToListD(List<M> mList);
}
