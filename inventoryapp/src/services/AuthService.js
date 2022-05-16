class AuthService {
    getCurrentCompanyName() {
        const companyName = sessionStorage.getItem('companyName');
        if(companyName === 'undefined' || !companyName) {
            return null;
        } else {
            return companyName;
        }
    }

    setUserSession(companyName) {
        sessionStorage.setItem('companyName', companyName);
    }

    setActiveProductList(activeProducts) {
        sessionStorage.setItem('activeProductsList', JSON.stringify(activeProducts));
    }

    getActiveProductList() {
        const productsList = JSON.parse(sessionStorage.getItem('activeProductsList'));
        if(productsList === 'undefined' || !productsList) {
            return null;
        } else {
            return productsList;
        }
    }

    resetUserSession() {
        sessionStorage.removeItem('companyName');
        sessionStorage.removeItem('activeProductsList');
    }


}

export default new AuthService();