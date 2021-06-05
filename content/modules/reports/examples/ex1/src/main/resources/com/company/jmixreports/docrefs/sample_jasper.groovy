import com.company.jmixreports.entity.LiteratureType

def result = []

def ltList = dataManager.load(LiteratureType.class).all().list();
ltList.each(lt->{
    def count = dataManager.loadValue("select sum(bi.bookCount) from jmxrpr_BookInstance bi where bi.libraryDepartment = :department and bi.bookPublication.book.literatureType = :lt ", Long.class)
            .parameter("department", params['library_department']).parameter("lt", lt)
            .one();

    def refCount = dataManager.loadValue("select sum(bi.bookCount) from jmxrpr_BookInstance bi where bi.libraryDepartment = :department and bi.bookPublication.book.literatureType = :lt and bi.isReference = true", Long.class)
            .parameter("department", params['library_department']).parameter("lt", lt)
            .one();
            
    result.add(['literature_type_name': lt.name,
            'books_instances_amount': count,
            'reference_books_instances_amount': refCount])
});
return result;
